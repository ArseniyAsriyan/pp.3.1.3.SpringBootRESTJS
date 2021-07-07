$(document).ready(function () {

    $('.RegisterButton').on('click', function () {
        let user = {
            login: $("#login").val(),
            name: $("#name").val(),
            surname: $("#surname").val(),
            age: $("#age").val(),
            email: $("#email").val(),
            password: $("#password").val(),
        }
        console.log(user);
        fetch("api/v1/registration", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)

        }).then(() => openLoginForm());
    });


    function openLoginForm() {
        document.location.replace("/login");
    }

});





