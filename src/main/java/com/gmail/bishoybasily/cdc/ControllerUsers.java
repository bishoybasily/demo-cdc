package com.gmail.bishoybasily.cdc;

import com.gmail.bishoybasily.cdc.repo.JpaRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class ControllerUsers {

    private RepositoryUsers repositoryUsers;

    public ControllerUsers(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @GetMapping
    public Iterable<User> all(Filter filter) {

//        StringBuilder builder = new StringBuilder("ORDER BY ");
//
//        StringJoiner joiner = new StringJoiner(", ");
//
//        filter.sort().forEach(order -> joiner.add(order.getProperty() + " " + order.getDirection().name()));
//
//        builder.append(joiner);
//
//        System.out.println(builder);

        EntityManager em = null;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("ax")));

        ParameterExpression<Date> parameter = criteriaBuilder.parameter(Date.class);
        criteriaBuilder.lessThanOrEqualTo(parameter, root.get("from"));
        TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setParameter(parameter, new Date(), TemporalType.DATE).getResultList();


        JpaRepo<User> userJpaRepo = new JpaRepo<>(em);

//        userJpaRepo.findAll(new JpaRepo.Ordering<User>() {
//            @Override
//            public void addOrders(CriteriaBuilder builder, CriteriaQuery<User> criteria, Root<User> root) {
//
//            }
//        }, new JpaRepo.Filtering<User>() {
//            @Override
//            public void addFilters(CriteriaBuilder builder, TypedQuery<User> criteria, Root<User> root) {
//
//            }
//        });


        return repositoryUsers.findAll();
    }

}
