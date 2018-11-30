$().ready(function () {
    $.validator.setDefaults({
        highlight: function (element) {
            $(element)
                .closest('.form-group')
                .addClass('has-error')
        },
        unhighlight: function (element) {
            $(element)
                .closest('.form-group')
                .removeClass('has-error')
        }
    });

    $('#userForm').validate({
        rules: {
            username: {
                required: true,
                minlength: 5,
                maxlength: 30
            },
            surname: "required",
            firstname: "required",
            password: {
                required: true,
                minlength: 8
            },
            birthDate: "required",
            confirmPassword: {
                required: true,
                minlength: 8,
                equalTo: "password"
            }
        },
        messages: {
            username: {
                required: "Please enter username",
                minlength: "Your username must be at least 8 characters long"
            },
            surname: "Please enter your surname",
            firstname: "Please enter your firstname",
            password: {
                required: "Please enter password",
                minlength: "Your password must be at least 8 characters long"
            },
            confirmPassword: {
                required: " Please enter password",
                minlength: "Your password must be at least 8 characters long",
                equalTo: "Please enter the same password as above"
            },
            birthDate: {
                required: "Please enter birthdate"
            }
        }
    });
});