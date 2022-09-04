const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();
let from;

function connectToChat(userName) {
    console.log("connecting to chat...")
    from=userName;
    console.log(from);
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        //stompClient.subscribe("/topic/messages/" + userName, function (response) {
        stompClient.subscribe("/topic/messages." + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.from) {
                $('#selectedUserId').html('');
                render(data.message, data.from);
            } else {
                newMessages.set(data.from, data.message);
                $('#userNameAppender_' + data.from).append('<span id="newMessage_' + data.from + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        from: from,
        message: text
    }));
}

function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (response) {
        connectToChat(userName);
    }).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!")
        }
    })
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll() {
    $.get(url + "/fetchAllUsers?excludedUser="+userEmail, function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            //connectToChat(users[i]);
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                '                <img src="https://img.icons8.com/ios-glyphs/344/user--v1.png" width="55px" height="55px" alt="avatar" />\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}
