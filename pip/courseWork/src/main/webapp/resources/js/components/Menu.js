$(document).ready(() => {
    var UserModel = Backbone.Model.extend({
        urlRoot: 'users'
    });

    var UserModel2 = Backbone.Model.extend({
       url:  'context?action=get_user'
    });

    window.Menu = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .exitButton': 'exit',
        },

        initialize() {
            this.el = $('.menu');

            _.bindAll(this, 'render');
            _.bindAll(this, 'chooseBlock');

            this.user = new UserModel2();
            this.render();

            this.user.fetch({
                success: () => {
                    this.chooseBlock();
                },
                fail: () => {
                    throw "Error in getting user from context";
                }
            });
        },

        exit: function () {
            let position = window.location.href;

            let user = new UserModel({
                action: "exit",
                login: "",
                email: "UN",
                password: "",
            });

            user.save();

            document.getElementsByClassName("registrationForm")[0].style.display = "block";
            document.getElementsByClassName("cabinetForm")[0].style.display = "none";
            document.getElementsByClassName("_login")[0].innerHTML = "";
        },

        chooseBlock: function () {
            let login = this.user.attributes.login;
            let name = this.user.attributes.name;
            let icon = this.user.attributes.icon;

            if (login === "") {
                document.getElementsByClassName("registrationForm")[0].style.display = "block";
                document.getElementsByClassName("cabinetForm")[0].style.display = "none";
                document.getElementsByClassName("_login")[0].innerHTML = "";
            } else {
                document.getElementsByClassName("registrationForm")[0].style.display = "none";
                document.getElementsByClassName("cabinetForm")[0].style.display = "block";
                document.getElementsByClassName("_login")[0].innerHTML = "Hello, ";

                if (name === "") {
                    document.getElementsByClassName("_login")[0].innerHTML += login;
                } else {
                    document.getElementsByClassName("_login")[0].innerHTML += name;
                }

                document.getElementsByClassName("icon")[0].src = icon;

                //Check for admin logins
                if (login === "VKkristi_tulpan") {
                    $('.cabinetBlock').append(`
                    <a href="admin.html#news" class="w3-bar-item w3-button">Admin</a>   
                    `);
                }
            }
        },

        render() {
            $(this.el).append(`
            <div class="w3-top">
            <div class="w3-bar w3-black w3-card w3-border-bottom w3-border-white">
              <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" 
               onclick="search()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
              <a href="index.html" class="w3-bar-item w3-button w3-padding-large">HOME</a>
              
              <div class="w3-dropdown-hover w3-hide-small">
                <button class="w3-padding-large w3-button" title="More">MYTHOLOGY <i class="fa fa-caret-down"></i></button>     
                <div class="w3-dropdown-content w3-bar-block w3-card-4 w3-black">
                  <a href="country.html?3" class="w3-bar-item w3-button">Ancient Rus</a>
                  <a href="country.html?2" class="w3-bar-item w3-button">Ancient Rome</a>
                  <a href="country.html?4" class="w3-bar-item w3-button">Ancient Egypt</a>  
                  <a href="country.html?1" class="w3-bar-item w3-button">Ancient Greece</a>    
                </div>
              </div>
              
              <a href="top.html" class="w3-bar-item w3-button w3-padding-large w3-hide-small">TOP-10</a>
              <a href="news.html" class="w3-bar-item w3-button w3-padding-large w3-hide-small">NEWS</a>
              
              <div class="registrationForm" style="display: block">
                <div class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right" onclick="new RegWindow();">Sign up</div>
                <div class="w3-bar-item w3-padding-large w3-hide-small w3-right">or</div>
                <div class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right" onclick="new LoginWindow();">Login</div>
              </div>
              
              <div class="cabinetForm w3-right w3-dropdown-hover" style="width: 100px;margin-right: 7%;display: none;">
                <img src="resources/images/profile1.png" class="w3-dropdown-hover w3-circle icon" alt="Norway" style="width: 30%;
                margin-top: 5%"> 
                <i class="fa fa-caret-down" style="margin-top: 30%"></i>
                <div class="w3-dropdown-content w3-bar-block w3-card-4 w3-black cabinetBlock">
                  <a class="w3-bar-item _login"></a>
                  <a href="" class="w3-bar-item w3-button exitButton">Logout</a> 
                </div>
              </div>
              
            </div>
          </div>
            `);
        }
    });
});