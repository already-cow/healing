package misoya.healing.domain.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum UserRole {
    NONE(null, "", 0),
    NORMAL("ROLE_NORMAL", "일반", 1),
    ADMIN("ROLE_ADMIN", "관리자", 11);

    private final String role;
    private final String name;
    private final int value;

    public static UserRole ofRole(String role) {
        return EnumSet.allOf(misoya.healing.domain.model.UserRole.class)
                .stream().filter(v -> v != UserRole.NONE && v.getRole().equals(role))
                .findAny()
                .orElse(UserRole.NONE);
    }
    @Override
    public String toString() {
        return this.getRole();
    }

    @Converter
    public static class UserRoleConverter implements AttributeConverter<UserRole, String> {

        @Override
        public String convertToDatabaseColumn(UserRole attribute) {
            return  attribute != null ? attribute.getRole() : UserRole.NONE.getRole();
        }

        @Override
        public UserRole convertToEntityAttribute(String dbData) {
            return UserRole.ofRole(dbData);
        }
    }
}
