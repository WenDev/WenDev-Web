package site.wendev.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author 江文
 */
@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @Id
    private String id;              // 主键

    @Field
    private String username;        // 用户名

    @Field
    private String password;        // 密码

    @Field
    private String nickname;        // 用户昵称

    @Field
    private String email;           // 用户邮箱

    @Field
    private String role;            // 用户权限

    @Indexed(name = "register-date", direction = IndexDirection.DESCENDING)
    @Field
    private Date registerDate;      // 注册日期

    @Version
    private Long version;           // 乐观锁
}
