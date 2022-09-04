$(document).ready(function () {
  $("#registerForm").submit(function (event) {
    var formData = {
      name: $("#name").val(),
      email: $("#email").val(),
      password: $("#password").val(),
    };

    $.ajax({
      headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
          },
      type: "POST",
      url: "/add/user",
      data: JSON.stringify(formData),
      dataType: "json",
    }).done(function (data) {
      console.log(data);
      alert(data);
    });

    event.preventDefault();
  });
});