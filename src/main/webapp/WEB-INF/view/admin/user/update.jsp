<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Dashboard</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script>
                    document.getElementById('avatarFile').addEventListener('change', function (event) {
                        var file = event.target.files[0];
                        if (file) {
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                document.getElementById('avatarPreview').src = e.target.result;
                                document.getElementById('avatarPreview').style.display = 'block';
                            };
                            reader.readAsDataURL(file);
                        }
                    });
                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <div id="layoutSidenav_nav">
                        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                            <jsp:include page="../layout/sidebar.jsp" />
                            <div class="sb-sidenav-footer">
                                <div class="small">Logged in as:</div>
                                Hỏi Dân IT
                            </div>
                        </nav>
                    </div>
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Users</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h2> Update User</h2>
                                            <hr>
                                            <form:form method="post" action="/admin/user/create" modelAttribute="user"
                                                enctype="multipart/form-data">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">ID:</label>
                                                    <form:input type="id" class="form-control" path="id" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Email:</label>
                                                    <form:input type="email" class="form-control" path="email"
                                                        disabled="true" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Full Name:</label>
                                                    <form:input type="fullName" class="form-control" path="fullName" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Address:</label>
                                                    <form:input type="address" class="form-control" path="address" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Phone:</label>
                                                    <form:input type="phone" class="form-control" path="phone" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Role:</label>
                                                    <form:select class="form-select" path="role.name">
                                                        <form:option value="ADMIN">ADMIN</form:option>
                                                        <form:option value="USER">USER</form:option>
                                                    </form:select>
                                                </div>
                                                <!-- <div class="mb-3">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input class="form-control" type="file" path="avatar"
                                                        accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                                </div> -->
                                                <div class="mb-3">
                                                    <label class="form-label">Avatar:</label>
                                                    <form:input type="file" class="form-control" path="avatar" />
                                                </div>
                                                <button type="submit" class="btn btn-primary">Update</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <!-- <script src="js/scripts.js"></script> -->
            </body>

            </html>