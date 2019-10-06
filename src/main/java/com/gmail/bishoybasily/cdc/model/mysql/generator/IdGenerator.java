package com.gmail.bishoybasily.cdc.model.mysql.generator;

import com.gmail.bishoybasily.cdc.model.mysql.entity.AbstractJpaEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

public class IdGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        if (obj instanceof AbstractJpaEntity) {
            String id = ((AbstractJpaEntity) obj).getId();
            if (id != null)
                return id;
        }

        return session.createNativeQuery("SELECT UUID()").list().iterator().next().toString();
    }

}
