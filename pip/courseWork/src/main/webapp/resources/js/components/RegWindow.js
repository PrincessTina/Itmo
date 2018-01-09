$(document).ready(() => {
    var UserModel = Backbone.Model.extend({
        urlRoot: 'users'
    });

    window.RegWindow = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .regButton': 'register',
            'click .regWindow .cross': 'closeWindow',
            'click .notice .cross': 'closeNotice'
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'closeLoginWindow');
            _.bindAll(this, 'closeWindow');

            this.closeLoginWindow();
            this.closeWindow();
            this.render();
        },

        register() {
            let username = document.getElementsByClassName("_username")[0];
            let email = document.getElementsByClassName("_email")[0];
            let password = document.getElementsByClassName("_password")[0];

            if (this.validate(username, email, password)) {
                let user = new UserModel({
                    action: "reg",
                    login: username.value,
                    email: email.value,
                    password: password.value,
                });

                let result = user.save();

                setTimeout(function () {
                    if (result.statusText === "OK") {
                        let element = document.getElementsByClassName("regWindow")[0];

                        if (element !== undefined) {
                            element.remove(0);
                        }

                        document.getElementsByClassName("notice")[0].style.display = "block";

                        setTimeout(function () {
                            location.reload();
                        }, 200);
                    } else if (result.statusText === "Internal Server Error") {
                        username.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                        username.title = "This username is already used"
                    }
                }, 200);
            }
        },

        validate(username, email, password) {
            //start condition
            username.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            username.title = "";
            email.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            password.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

            let condition = true;

            if (username.value === "" || /\s+/.test(username.value) || /^VK.*$/.test(username.value) || /^IN.*$/.test(username.value)) {
                username.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                condition = false;
            }

            if (email.value === "" || /\s+/.test(email.value) ||
                !/.+@(mail.ru|bk.ru|list.ru|inbox.ru|gmail.com|yandex.ru)$/.test(email.value)) {
                email.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                condition = false;
            }

            if (password.value === "" || /\s+/.test(password.value)) {
                password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                condition = false;
            }

            return condition;
        },

        closeNotice() {
            let element = document.getElementsByClassName("notice")[0];

            if (element !== undefined) {
                element.remove(0);
            }
        },

        closeWindow() {
            let element = document.getElementsByClassName("regWindow")[0];

            if (element !== undefined) {
                element.remove(0);
            }
        },

        closeLoginWindow() {
            let element = document.getElementsByClassName("loginWindow")[0];

            if (element !== undefined) {
                element.remove(0);
            }
        },

        render() {
            $(this.el).append(`
        <div class="w3-card-4 w3-round-large w3-padding regWindow" 
            style="width: 60%; position: fixed; z-index: 100; top: 30%; left: 20%;">

            <div class="w3-container w3-center">
                <div class="w3-section">
	                <div class="w3-right-align">
                        <img class="cross" src="resources/images/cross.png" alt="" style="margin-top: 0; width: 17px; height: 17px;">
                    </div>
                    <div class="w3-center w3-border-bottom w3-border-lime">
                        <h5 class="w3-text-white">Регистрация</h5>
                    </div>
                </div>
 	            <div class="w3-section">
    	            <img src="resources/images/icon1.png" alt="" style="width: 17px; height: 17px;">
    	            <input class="_username" type="text" placeholder="Username" style="width: 60%;">   
	            </div>
                <div class="w3-section">
    	            <img src="resources/images/icon3.png" alt="" style="width: 17px; height: 17px;">
    	            <input class="_email" type="text" placeholder="Email" style="width: 60%;">   
	            </div>
                <div class="w3-section">
    	            <img src="resources/images/icon2.png" alt="" style="width: 17px;height: 17px;">
    	            <input class="_password" type="password" placeholder="Password" style="width: 60%">   
	            </div>
	            
	            <button class="w3-button w3-lime w3-center regButton" style="width: 59%; margin-left: 2%;">Зарегистрироваться</button>
            </div>
        </div>
        
        <div class="w3-card-4 w3-padding w3-panel w3-green w3-display-container notice" style="position: fixed; top: 30%; 
            right: 0; display: none">
              <span class="w3-button w3-green w3-large w3-display-topright cross">×</span>
              <h3>Success!</h3>
              <p>You have successfully registered</p>
            </div>
        `);
        }
    });
});