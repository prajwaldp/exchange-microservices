package contentservice.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Table
public class Article {

  @PrimaryKey private String id;
  
  private String title;
  private String content;

  @Override
  public String toString() {
  	return String.format("<Article title=`%s`, content=`%s`", this.getTitle(), this.getContent());
  }
}
