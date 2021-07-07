$(document).ready(function () {
    restartAllUser();
    $('.AddBtn').on('click', function (event) {
        let user = {
            login: $("#login").val(),
            name: $("#name").val(),
            surname: $("#surname").val(),
            age: $("#age").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            roles: getRole("#selectRole")

        }
        console.log(user);
        fetch("api/v1/new_user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('nav-home'))
            .then(() => restartAllUser());
        $('input').val('');
    });
});

function createTableRow(u) {  //
    let userRole = "";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].role.substr(5);
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.login}</td>
            <td>${u.name}</td>
            <td>${u.surname}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/api/v1/${u.id}" class="btn btn-info eBtn">Edit</a>
            </td>
            <td>
            <a href="/api/v1/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), rolesSet: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}

function restartAllUser() {
    let UserTableBody = $("#users_table")

    UserTableBody.children().remove();

    fetch("api/v1/all_users")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

document.addEventListener('click', function (event) {
    event.preventDefault()




    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #editModal").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #loginEd").val(user.login);
            $(".editUser #nameEd").val(user.name);
            $(".editUser #surnameEd").val(user.surname);
            $(".editUser #ageEd").val(user.age);
            $(".editUser #emailEd").val(user.email);
            $(".editUser #selectRoleEd").val(user.roles);
        });
    }


    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        $(".deleteUser #deleteModal").modal();

        $.get(href, function (user) {
            $(".deleteUser #idDel").val(user.id);
            $(".deleteUser #loginDel").val(user.login);
            $(".deleteUser #nameDel").val(user.name);
            $(".deleteUser #surnameDel").val(user.surname);
            $(".deleteUser #ageDel").val(user.age);
            $(".deleteUser #emailDel").val(user.email);
            $(".deleteUser #selectRoleDel").val(user.roles);
        });
    }


    if ($(event.target).hasClass('editButton')) {
        let user = {
            id:$('#id').val(),
            login:$('#loginEd').val(),
            name:$('#nameEd').val(),
            surname:$('#surnameEd').val(),
            age:$('#ageEd').val(),
            email:$('#emailEd').val(),
            roles: getRole("#selectRoleEd")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('deleteButton')) {
        let id = $('#idDel').val();

        console.log(id);
        delModalButton(id);
    }





    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});


function editModalButton(user) {
    fetch("api/v1/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #editModal').modal('hide');
        restartAllUser();
    })
}

function delModalButton(id) {

    fetch("api/v1/delete/" + id, {
        method: 'DELETE',
    }).then(function (response) {
        $('input').val('');
        $('.deleteUser #deleteModal').modal('hide');
        restartAllUser();
    })
}



function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}
function logout(){
    document.location.replace("/logout");
}
function userTable(){
    document.location.replace("/user");
}