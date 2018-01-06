$(document).ready(() => {
    var UserModel = Backbone.Model.extend({
        urlRoot: 'users'
    });

    window.RegWindow = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .regButton': 'register',
            'click .regWindow .cross': 'closeWindow'
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'closeLoginWindow');

            this.closeLoginWindow();
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

                user.save();
            }
        },

        validate(username, email, password) {
            //start condition
            username.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            email.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            password.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

            if (username.value === "" || /\s+/.test(username.value)) {
                username.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                return false;
            }

            if (email.value === "" || /\s+/.test(email.value) ||
                !/@(mail.ru|bk.ru|list.ru|inbox.ru|gmail.ru)$/.test(email.value)) {
                email.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                return false;
            }

            if (password.value === "" || /\s+/.test(password.value)) {
                password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                return false;
            }

            return true;
        },

        closeWindow() {
            document.getElementsByClassName("regWindow")[0].remove(0);
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
            style="width: 60%; position: fixed; z-index: 100; top: 30%; left: 0;">

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
    	            <img class="icon" src="resources/images/icon1.png" alt="" style="width: 17px; height: 17px;">
    	            <input class="_username" type="text" placeholder="Username" style="width: 60%;">   
	            </div>
                <div class="w3-section">
    	            <img class="icon" src="resources/images/icon3.png" alt="" style="width: 17px; height: 17px;">
    	            <input class="_email" type="text" placeholder="Email" style="width: 60%;">   
	            </div>
                <div class="w3-section">
    	            <img class="icon" src="resources/images/icon2.png" alt="" style="width: 17px;height: 17px;">
    	            <input class="_password" type="password" placeholder="Password" style="width: 60%">   
	            </div>
	            
	            <button class="w3-button w3-lime w3-center regButton" style="width: 59%; margin-left: 2%;">Зарегистрироваться</button>
            </div>
        </div>
        `);
        }
    });
});