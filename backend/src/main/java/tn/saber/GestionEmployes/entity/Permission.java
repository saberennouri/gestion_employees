package tn.saber.GestionEmployes.entity;

import jakarta.persistence.*;

@Entity
@Table(name="permissions",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Permission {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false,unique = true)
  private String name;
  @Column(length = 255)
  private String description;
  public Permission() {}
  public Permission(String name, String description) {
    this.name = name;
    this.description = description;
  }
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
}
