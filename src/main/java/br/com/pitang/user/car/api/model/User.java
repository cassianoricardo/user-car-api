package br.com.pitang.user.car.api.model;

import br.com.pitang.user.car.api.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fistName;

  private String lastName;

  @Column(unique = true)
  private String email;

  private Date birtday;

  private String phone;
  @OneToMany
  @JoinColumn(name="user_id")
  private List<Car> cars;

  @Column(unique = true)
  private String login;

  @JsonIgnore
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_role",
          joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_id"),name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_role_id"), name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;


  //region getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFistName() {
    return fistName;
  }

  public void setFistName(String fistName) {
    this.fistName = fistName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirtday() {
    return birtday;
  }

  public void setBirtday(Date birtday) {
    this.birtday = birtday;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Car> getCars() {
    return cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }


  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
//endregion

  public UserDTO parseToDTO(){
    return UserDTO.builder().id(this.getId())
            .fistName(this.getFistName())
            .lastName(this.getLastName())
            .email(this.getEmail())
            .login(this.getUsername())
            .phone(this.getPhone())
            .birtday(this.getBirtday())
            .build();
  }
}
