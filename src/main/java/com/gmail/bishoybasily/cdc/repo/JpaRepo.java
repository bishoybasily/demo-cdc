package com.gmail.bishoybasily.cdc.repo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

public class JpaRepo<T> {

    private EntityManager entityManager;

    public JpaRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Flux<T> findAll(List<Sort> sorts, List<Filter> filters, Page page) {
        return Mono.fromCallable(() -> {

            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T> cls = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cls);
            Root<T> root = criteriaQuery.from(cls);

            sorts.forEach(sort -> {

                if (sort.dir.equals(Sort.Dir.ASC))
                    criteriaQuery.getOrderList().add(criteriaBuilder.asc(root.get(sort.prop)));

                if (sort.dir.equals(Sort.Dir.DESC))
                    criteriaQuery.getOrderList().add(criteriaBuilder.desc(root.get(sort.prop)));

            });

            TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);

            filters.forEach(filter -> {

                ParameterExpression parameter = criteriaBuilder.parameter(filter.cls);

                switch (filter.proc) {
                    case EQ:
                        criteriaBuilder.equal(parameter, root.get(filter.prop));
                        break;
                    case LE:
                        criteriaBuilder.lessThan(parameter, root.get(filter.prop));
                        break;
                    case GE:
                        criteriaBuilder.greaterThan(parameter, root.get(filter.prop));
                        break;
                    case LEQ:
                        criteriaBuilder.lessThanOrEqualTo(parameter, root.get(filter.prop));
                        break;
                    case GEQ:
                        criteriaBuilder.greaterThanOrEqualTo(parameter, root.get(filter.prop));
                        break;
                }

                if (filter.temporalType != null)
                    typedQuery.setParameter(parameter, (Date) filter.val, filter.temporalType);
                else
                    typedQuery.setParameter(parameter, filter.val);

            });

            typedQuery.setFirstResult(page.index * page.count);
            typedQuery.setMaxResults(page.count);

            return typedQuery;

        }).flatMapIterable(TypedQuery::getResultList);
    }

    static class Page {

        private Integer index;
        private Integer count;

        public Page(Integer index, Integer count) {
            this.index = index;
            this.count = count;
        }
    }

    static class Sort {

        private String prop;
        private Dir dir;

        public Sort(String prop, Dir dir) {
            this.prop = prop;
            this.dir = dir;
        }

        public String getProp() {
            return prop;
        }

        public Dir getDir() {
            return dir;
        }

        enum Dir {
            ASC, DESC
        }
    }

    static class Filter<T> {

        private String prop;
        private Proc proc;
        private T val;
        private Class<T> cls;
        private TemporalType temporalType;

        public Filter(String prop, Proc proc, T val, Class<T> cls) {
            this.prop = prop;
            this.proc = proc;
            this.val = val;
            this.cls = cls;
        }

        public Filter(String prop, Proc proc, T val, Class<T> cls, TemporalType temporalType) {
            this.prop = prop;
            this.proc = proc;
            this.val = val;
            this.cls = cls;
            this.temporalType = temporalType;
        }

        public String getProp() {
            return prop;
        }

        public Proc getProc() {
            return proc;
        }

        public Class<T> getCls() {
            return cls;
        }

        public TemporalType getTemporalType() {
            return temporalType;
        }

        public T getVal() {
            return val;
        }

        enum Proc {

            EQ, LE, GE, LEQ, GEQ

        }

    }

}
