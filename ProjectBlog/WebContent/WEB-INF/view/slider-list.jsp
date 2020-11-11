<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="x-ua-compatible" content="ie=edge">

  <title>Administration</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
    </ul>

    
    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Messages Dropdown Menu -->
      
      <li class="nav-item dropdown">
        <a class="nav-link" href="contact-list" aria-expanded="false">
          <i class="far fa-comments"></i>
          <c:if test="${contactCount != 0}">
          	<span class="badge badge-danger navbar-badge">${contactCount}</span>
          </c:if>
        </a>
      </li>
      
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-user"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <a href="user-edit" class="dropdown-item">
            <!-- Message Start -->
            <div class="media align-items-center">
              <img src="${user.getPicture()}" style="max-width: 80px; max-height: 80px;" alt="User Avatar" class="img-brand-50 mr-3 img-circle">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  ${user.getName()}
                </h3>
              </div>
            </div>
            <!-- Message End -->
          </a>
          <div class="dropdown-divider"></div>
          <a href="user-change-password" class="dropdown-item">
            <i class="fas fa-user"></i> Change Password
          </a>
          <div class="dropdown-divider"></div>
          <div class="dropdown-item">
          
          	<form:form action="${pageContext.request.contextPath}/logout">
          		<input type="submit" value="Sign Out">
          	</form:form>
          </div>
        </div>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Product Logo -->
    <a href="index3.html" class="brand-link">
      <img src="${pageContext.request.contextPath}/dist/img/AdminLTELogo.png" alt="Cubes School Logo" class="brand-image img-circle elevation-3"
           style="opacity: .8">
      <span class="brand-text font-weight-light">Cubes School</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Slider
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="slider-list" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Slider list</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="slider-form" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Add slider</p>
                </a>
              </li>
            </ul>
          </li>
          
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Blog
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="blog-list" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Blog list</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="blog-form" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Add blog</p>
                </a>
              </li>
            </ul>
          </li>
          
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Category
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="category-list" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Category list</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="category-form" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Add category</p>
                </a>
              </li>
            </ul>
          </li>
          
          <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Tag
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="tag-list" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Tag list</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="tag-form" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Add tag</p>
                </a>
              </li>
            </ul>
          </li>
          
          <li class="nav-item has-treeview">
            <a href="comment-list" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Comments
              </p>
            </a>
          </li>
          
          <li class="nav-item has-treeview">
            <a href="contact-list" class="nav-link">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Messages
              </p>
            </a>
          </li>
          
         
          
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Slider</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item"><a href="#">Slider</a></li>
              <li class="breadcrumb-item active">Slider list</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">All sliders</h3>
                
                <div class="card-tools">
	                <a href="slider-form" class="btn btn-success">
	                    <i class="fas fa-plus-square"></i>
	                    Add new slider
	                </a>
                </div>
                
              </div>
              
              
              <!-- /.card-header -->
              <div class="card-body">
                <table class="table table-bordered">
                  <thead>                  
                    <tr>
                      <th style="width: 10px">#ID</th>
                      <th class="text-center">Title</th>
                      <th class="text-center">Picture</th>
                      <th class="text-center">Button-Title</th>
                      <th class="text-center">Button-Url</th>
                      <th class="text-center">Action</th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:forEach items="${sliderList}" var="slider">
	                    <tr>
	                      <td class="text-center">#${slider.id}</td>
	                       <td class="text-center">${slider.title}</td>
	                      <td class="text-center">
	                        <img src="${slider.picture}" style="max-width: 80px;">
	                      </td>
	                      <td class="text-center">${slider.buttonTitle}</td>
	                      <td class="text-center">${slider.buttonUrl}</td>
	                      <td class="text-center">
	                        <div class="btn-group">
	                        	
		                          <a href="slider-enable?id=${slider.id}" class="btn btn-info">
		                          	<c:if test="${slider.enable}">
		                            	<i class="fas fa-eye"></i>
		                            </c:if>
		                            <c:if test="${!slider.enable}">
		                            	<i class="fas fa-eye-slash"></i>
		                            </c:if>
		                          </a>
		                          
	                          <a href="slider-update-form?id=${slider.id}" class="btn btn-info">
	                            <i class="fas fa-edit"></i>
	                          </a>
	                          <button type="button" class="btn btn-info" data-toggle="modal" data-target="#delete-modal-${slider.id}">
	                            <i class="fas fa-trash"></i>
	                          </button>
	                        </div>
	                      </td>
	                    </tr>
	                    
	                     <div class="modal fade" id="delete-modal-${slider.id}">
						    <div class="modal-dialog">
						      <div class="modal-content">
						        <div class="modal-header">
						          <h4 class="modal-title">Delete Slider</h4>
						          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						            <span aria-hidden="true">&times;</span>
						          </button>
						        </div>
						        <div class="modal-body">
						          <p>Are you sure you want to delete slider?</p>
						          <strong></strong>
						        </div>
						        <div class="modal-footer justify-content-between">
						          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						          <a href="slider-delete?id=${slider.id}" class="btn btn-danger" >Delete</a>
						        </div>
						      </div>
						      <!-- /.modal-content -->
						    </div>
						    <!-- /.modal-dialog -->
						  </div>
	                    
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer clearfix">
                
              </div>
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  

  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
    <div class="float-right d-none d-sm-inline">
      PHP Laravel
    </div>
    <!-- Default to the left -->
    <strong>Copyright &copy; 2019 <a href="https://cubes.edu.rs">Cubes School</a>.</strong> All rights reserved.
  </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/dist/js/adminlte.min.js"></script>
</body>
</html>
