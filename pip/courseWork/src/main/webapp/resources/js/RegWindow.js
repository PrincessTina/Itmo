$(document).ready(function () {
    window.RegWindow = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .regButton': 'register',
            'click .cross': 'closeWindow',
        },

        initialize: function () {
            _.bindAll(this, 'render');

            this.render();
        },

        register: function () {
            let root = document.getElementsByClassName("reg_window")[0];

            let user = new UserModel({
                action: "reg",
                login: root.getElementsByClassName("_username")[0].value,
                email: root.getElementsByClassName("_email")[0].value,
                password: root.getElementsByClassName("_password")[0].value,
            });

            user.save();
        },

        closeWindow: function () {
            document.getElementsByClassName("reg_window")[0].remove(0);
        },

        render: function () {
            $(this.el).append(`
        <div class="reg_window">
            <label style="text-align: center">Регистрация</label>
            <img class="cross" src="resources/images/cross.png" alt=""/>

            <div class="fields">
                <div class="field">
                    <img class="icon" src="resources/images/icon1.png" alt=""/>
                    <input class="input _username" type="text" placeholder="Username"/>        
                </div>
                <div class="field">
                    <img class="icon" src="resources/images/icon3.png" alt=""/>
                    <input class="input _email" type="text" placeholder="Email"/>        
                </div>
                <div class="field">
                    <img class="icon" src="resources/images/icon2.png" alt=""/>
                    <input class="input _password" type="password" placeholder="Password" />            
                </div>
                <button class="regButton">Зарегистрироваться</button>
            </div>
        </div>
        `);
        }
    });
});