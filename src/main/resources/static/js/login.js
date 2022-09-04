$(document).ready(function () {
  $("#loginForm").submit(function (event) {
    var formData = {
      //name: $("#name").val(),
      username: $("#username").val(),
      password: $("#password").val(),
    };

    $.ajax({
      type: "POST",
      url: "/authenticate",
      data: JSON.stringify(formData),
      dataType: "json",
    }).done(function (data) {
      console.log(data);
      alert(data);
    });

    event.preventDefault();
  });
});