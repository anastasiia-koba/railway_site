package system.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import system.entity.UserProfile;
import system.service.UserService;

/**
 * Validator for {@link system.entity.UserProfile} class;
 * implements {@link Validator} interface.
 */

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserProfile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserProfile user = (UserProfile) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (user.getLogin().length() < 8 || user.getLogin().length() > 32){
            errors.rejectValue("login", "Size.userForm.username");
        }

        if (userService.findByUserName(user.getLogin()) != null) {
            errors.rejectValue("login", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPass().length() < 8 || user.getPass().length() > 32){
            errors.rejectValue("pass", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPass())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
