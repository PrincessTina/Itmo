$(document).ready(() => {
    window.LoginWindow = Backbone.View.extend({
        el: $('body'),

        initialize() {
            _.bindAll(this, 'render');

            this.render();
        },

        render() {
            $(this.el).append(`
        <div class="reg_window">
            <label style="text-align: center">Вход</label>

            <div class="fields">
                <div class="field">
                    <img class="icon" src="resources/images/icon1.png" alt=""/>
                    <input class="input" type="text" placeholder="Username"/>        
                </div>
                <div class="field">
                    <img class="icon" src="resources/images/icon2.png" alt=""/>
                    <input class="input" type="password" placeholder="Password" />            
                </div>
                <button class="regButton" onclick="">Войти</button>
            </div>
        </div>
        `);
        }
    });
});