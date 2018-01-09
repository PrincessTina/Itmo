$(document).ready(() => {
    var UserModel = Backbone.Model.extend({
        urlRoot: 'users'
    });

    window.LoginWindow = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .loginWindow .cross': 'closeWindow',
            'click .loginButton': 'check',
            'click ._vk': 'vkAuth',
            'click ._in': 'inAuth',
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'closeRegWindow');
            _.bindAll(this, 'closeWindow');

            this.closeWindow();
            this.closeRegWindow();
            this.render();
        },

        vkAuth() {
            let location = "vk";

            window.location.href = "https://oauth.vk.com/authorize?" +
                "client_id=6323215&" +
                "display=popup&" +
                "scope=email&" +
                "redirect_uri=" + window.location +
                "auth?address=" + location + window.location;
        },

        inAuth() {
            let location = "in";
            let url = "http://localhost:62434/courseWork-1.0-SNAPSHOT/"; //то, что зашито в инсте

            window.location.href = "https://api.instagram.com/oauth/authorize/?" +
                "client_id=39649a7b64a54a898f1f972c68eb1e26&" +
                "&response_type=code&" +
                "redirect_uri=" + url +
                "auth?address=" + location + window.location;
        },

        check() {
            let username = document.getElementsByClassName("_username")[0];
            let password = document.getElementsByClassName("_password")[0];

            //start condition
            username.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            password.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

            let user = new UserModel({
                action: "check",
                login: username.value,
                email: "UN",
                password: password.value,
            });

            let result = user.save();

            setTimeout(function () {
                if (result.statusText === "OK") {
                    let element = document.getElementsByClassName("loginWindow")[0];

                    if (element !== undefined) {
                        element.remove(0);
                    }

                    location.reload();
                } else if (result.statusText === "Internal Server Error") {
                    username.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                    password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                }
            }, 200);
        },

        closeWindow() {
            let element = document.getElementsByClassName("loginWindow")[0];

            if (element !== undefined) {
                element.remove(0);
            }
        },

        closeRegWindow() {
            let element = document.getElementsByClassName("regWindow")[0];

            if (element !== undefined) {
                element.remove(0);
            }
        },

        render() {
            $(this.el).append(`
        <div class="w3-card-4 w3-round-large w3-padding loginWindow" 
            style="width: 60%; position: fixed; z-index: 100; top: 30%; left: 20%;">

            <div class="w3-container w3-center">
                <div class="w3-section">
	                <div class="w3-right-align">
                        <img class="cross" src="resources/images/cross.png" alt="" style="margin-top: 0; width: 17px; height: 17px;">
                    </div>
                    <div class="w3-center w3-border-bottom w3-border-lime">
                        <h5 class="w3-text-white">Вход</h5>
                    </div>
                </div>
 	            <div class="w3-section">
    	            <img class="icon" src="resources/images/icon1.png" alt="" style="width: 17px; height: 17px;">
    	            <input class="_username" type="text" placeholder="Username" style="width: 60%;">   
	            </div>
                <div class="w3-section">
    	            <img class="icon" src="resources/images/icon2.png" alt="" style="width: 17px;height: 17px;">
    	            <input class="_password" type="password" placeholder="Password" style="width: 60%">   
	            </div>
	            
	            <button class="w3-button w3-lime w3-center loginButton" style="width: 59%; margin-left: 2%;">Войти</button>
	            
	            <footer class="w3-container w3-white w3-padding w3-large" style="margin-left: 1%;margin-top: 2%;opacity: 0.9;">
                  <i class="fa fa-facebook-official w3-hover-opacity"></i>
                  <i class="fa fa-instagram w3-hover-opacity _in"></i>
                  <i class="fa fa-twitter w3-hover-opacity"></i>
                  <i class="fa fa-vk w3-hover-opacity _vk"></i>      
                </footer>
            </div>
        </div>
        `);
        }
    });
});