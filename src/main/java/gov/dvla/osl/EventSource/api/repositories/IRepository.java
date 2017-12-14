package gov.dvla.osl.EventSource.api.repositories;

import java.util.List;

public interface IRepository<Taggregate, TKey> {

    void add(Taggregate aggregate);
    void save(Taggregate aggregate);
    void update(Taggregate aggregate);
    void delete(Taggregate aggregate);

    String find(TKey id);
    List<Taggregate> findAll();

    long count();


}
