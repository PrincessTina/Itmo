$(document).ready(() => {
    window.Menu = Backbone.View.extend({
        el: null,

        initialize() {
            this.el = $('.menu');

            _.bindAll(this, 'render');

            this.render();
        },

        render() {
            $(this.el).append(`
            <div class="w3-top">
            <div class="w3-bar w3-black w3-card">
              <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" 
               onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
              <a href="#" class="w3-bar-item w3-button w3-padding-large">ГЛАВНАЯ</a>
              
              <div class="w3-dropdown-hover w3-hide-small">
                <button class="w3-padding-large w3-button" title="More">СТРАНА <i class="fa fa-caret-down"></i></button>     
                <div class="w3-dropdown-content w3-bar-block w3-card-4">
                  <a href="#" class="w3-bar-item w3-button">Древняя Русь</a>
                  <a href="#" class="w3-bar-item w3-button">Древний Рим</a>
                  <a href="#" class="w3-bar-item w3-button">Египет</a>  
                  <a href="#" class="w3-bar-item w3-button">Греция</a>    
                </div>
              </div>
              
              <a href="#" class="w3-bar-item w3-button w3-padding-large w3-hide-small">ТОП-10</a>
              <a href="#" class="w3-bar-item w3-button w3-padding-large w3-hide-small">НОВОСТИ</a>
              <div class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right" onclick="new RegWindow();">Зарегистрироваться</div>
              <div class="w3-bar-item w3-padding-large w3-hide-small w3-right">или</div>
              <div class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right" onclick="new LoginWindow();">Войти</div>
            </div>
          </div>
            `);
        }
    });
});