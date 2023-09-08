const URL = "http://localhost:8080/api/admin/users";

const roleList = [];

$(document).ready(function() {
    getAllUsers();
    fetch("http://localhost:8080/api/admin/roles")
        .then(response => response.json())
        .then(roles => {
            roleList.push(...roles);
        });
});

function getAllUsers() {
    const usersTable = $('.users-table');
    usersTable.empty();
    fetch(URL)
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                let row = `
                    <tr>
                        <td>${user.uuid}</td>
                        <td>${user.vkid}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.sex}</td>
                        <td>${user.roles.map(r => r.name.substring(5))}</td>  
                        <td>
                            <button type="button" class="btn btn-info text-white" data-bs-toggle="modal"
                            onclick="showEditModal('${user.uuid}')">Edit</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" 
                            onclick="showDeleteModal('${user.uuid}')">Delete</button>
                        </td>
                    </tr>
                `;
                usersTable.append(row);
            });
        })
        .catch(err => console.log(err));
}

// editModal
function showEditModal(uuid) {
    const editModal = new bootstrap.Modal($('.edit-modal'));
    const editForm = $('#edit-form')[0];
    showModal(editForm, editModal, uuid);
    editForm.addEventListener('submit', (ev) => {
        ev.preventDefault();
        ev.stopPropagation();
        let newUser = {
            uuid: $(`[name="uuid"]`, editForm).val(),
            firstName: $(`[name="firstName"]`, editForm).val(),
            lastName: $(`[name="lastName"]`, editForm).val(),
            password: $(`[name="password"]`, editForm).val(),
            codeFlow: $(`[name="codeFlow"]`, editForm).val(),
            sex: parseInt($(`[name="sex"]`, editForm).val()),
            birthdayDate: $(`[name="birthdayDate"]`, editForm).val(),
            isClosed: $(`[name="isClosed"]`, editForm).is(":checked"),
            photoId: $(`[name="photoId"]`, editForm).val(),
            roles: getRole(editForm)
        };
        fetch(URL + `/${uuid}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newUser)
        }).then(r => {
            editModal.hide();
            $('#users-table-tab')[0].click();
            if (!r.ok) {
                alert("User with this email already exists!!");
            }
        });
    });
}

// deleteModal
function showDeleteModal(uuid) {
    const deleteModal = new bootstrap.Modal($('.delete-modal'));
    const deleteForm = $('#delete-form')[0];
    showModal(deleteForm, deleteModal, uuid);
    deleteForm.addEventListener('submit', (ev) => {
        ev.preventDefault();
        ev.stopPropagation();
        fetch(URL + `/${uuid}`, {
            method: 'DELETE'
        }).then(r => {
            deleteModal.hide();
            $('#users-table-tab')[0].click();
            if (!r.ok) {
                alert("Deleting process failed!!");
            }
        });
    });
}

// Utils

function showModal(form, modal, uuid) {
    modal.show();
    fillRoles(form);
    fetch(URL + `/${uuid}`).then(response => {
        response.json().then(user => {
            $(`[name="uuid"]`, form).val(user.uuid);
            $(`[name="firstName"]`, form).val(user.firstName);
            $(`[name="lastName"]`, form).val(user.lastName);
            $(`[name="password"]`, form).val(user.password);
            $(`[name="codeFlow"]`, form).val(user.codeFlow);
            $(`[name="sex"]`, form).val(user.sex);
            $(`[name="birthdayDate"]`, form).val(user.birthdayDate);
            $(`[name="isClosed"]`, form).prop("checked", user.isClosed);
            $(`[name="photoId"]`, form).val(user.photoId);
        });
    });
}

function fillRoles(form) {
    roleList.forEach(role => {
        let option = `<option value="${role.id}">
                                 ${role.name}
                            </option>`;
        $(`[name="roles"]`, form).append(option);
    });
}

function getRole(form) {
    let role = [];
    let options = $(`[name="roles"]`, form)[0].options;
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            role.push(roleList[i]);
        }
    }
    return role;
}