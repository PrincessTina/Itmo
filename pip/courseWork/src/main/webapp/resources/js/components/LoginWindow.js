$(document).ready(() => {
    window.LoginWindow = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .loginWindow .cross': 'closeWindow'
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'closeRegWindow');

            this.closeRegWindow();
            this.render();
        },

        closeWindow() {
            document.getElementsByClassName("loginWindow")[0].remove(0);
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
            style="width: 60%; position: fixed; z-index: 100; top: 30%; left: 0;">

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
            </div>
        </div>
        `);
        }
    });
});