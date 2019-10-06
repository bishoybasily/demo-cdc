package com.gmail.bishoybasily.cdc.model.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.bishoybasily.cdc.Constants;
import com.gmail.bishoybasily.cdc.model.debezium.Change;
import com.gmail.bishoybasily.cdc.model.elasticsearch.entity.ElasticSearchAccount;
import com.gmail.bishoybasily.cdc.model.elasticsearch.entity.ElasticSearchUser;
import com.gmail.bishoybasily.cdc.model.elasticsearch.reporitory.ElasticSearchRepositoryUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChangesListener {

    private final ObjectMapper objectMapper;
    private final ElasticSearchRepositoryUsers users;

    @KafkaListener(topics = {
            "dbserver1.bank.users",
            "dbserver1.bank.accounts"
    })
    public void consume(List<Change> changes, Acknowledgment acknowledgment) {

        changes.stream()
                .filter(Objects::nonNull)
                .sorted()
                .forEach(change -> {

                    switch (change.getOperation()) {
                        case CREATE:
                        case UPDATE:

                            switch (change.getTable()) {
                                case Constants.Entities.USERS:
                                    ElasticSearchUser newUser = objectMapper.convertValue(change.getPayload().getAfter(), ElasticSearchUser.class);
                                    users.save(newUser);
                                    break;
                                case Constants.Entities.ACCOUNTS:
                                    ElasticSearchAccount account = objectMapper.convertValue(change.getPayload().getAfter(), ElasticSearchAccount.class);
                                    ElasticSearchUser oldUser = users.findById(account.getUserId()).orElseThrow(RuntimeException::new);
                                    oldUser.getAccounts().remove(account);
                                    oldUser.getAccounts().add(account);
                                    users.save(oldUser);
                                    break;
                            }

                            break;

                        case DELETE:

                            switch (change.getTable()) {
                                case Constants.Entities.USERS:
                                    ElasticSearchUser user = objectMapper.convertValue(change.getPayload().getBefore(), ElasticSearchUser.class);
                                    users.delete(user);
                                    break;
                                case Constants.Entities.ACCOUNTS:
                                    ElasticSearchAccount account = objectMapper.convertValue(change.getPayload().getBefore(), ElasticSearchAccount.class);
                                    ElasticSearchUser oldUser = users.findById(account.getUserId()).orElseThrow(RuntimeException::new);
                                    oldUser.getAccounts().add(account);
                                    oldUser.getAccounts().remove(account);
                                    users.save(oldUser);
                                    break;
                            }

                            break;
                    }


                    try {
                        System.out.println(objectMapper.writeValueAsString(change.getPayload()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

        acknowledgment.acknowledge();
    }

}
