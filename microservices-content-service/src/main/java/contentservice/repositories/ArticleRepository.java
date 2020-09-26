package contentservice.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import contentservice.models.Article;

@Repository
public interface ArticleRepository extends CassandraRepository<Article, String> {

}
