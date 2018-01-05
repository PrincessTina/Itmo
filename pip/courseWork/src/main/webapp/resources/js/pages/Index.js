$(document).ready(() => {
    window.Index = Backbone.View.extend({
        el: $('body'),
        slider: null,
        menu: null,

        initialize() {
            _.bindAll(this, 'render');
            this.render();

            this.menu = new Menu();
            this.slider = new IndexSlider();
        },

        render() {
            $(this.el).append(`
        <div>
          <!-- Меню -->
          <div class="menu"></div>
          
          <div class="w3-content" style="max-width:2000px;margin-top:46px">
            <!-- Слайдер -->
            <div class="slider"></div>
            
            <!-- Описание -->
            <div class="w3-container w3-content w3-center w3-padding-64" style="max-width:800px" id="band">
              <h2 class="w3-wide">МИФОЛОГИЯ РАЗНЫХ СТРАН</h2>
              <p class="w3-opacity"><i>Мы любим истории</i></p>
              <p class="w3-justify">
               <br/>
               Данный ресурс предоставляет вохможности сыграть в игру, прочесть и оценить мифы и легенды, представленные на сайте,
               пройти короткий тест, который даст нам возможность подсказывать вам истории по интересам.
              </p>
            </div>
            
            <!-- Новости -->
            <div class="w3-black" id="tour">
              <div class="w3-container w3-content w3-padding-64" style="max-width:800px">
                <h2 class="w3-wide w3-center">Новости</h2>
                <p class="w3-opacity w3-center"></p><br>
                <div class="w3-row-padding w3-padding-32" style="margin:0 -16px">
                  <div class="w3-third w3-margin-bottom">
                    <img src="https://www.w3schools.com/w3images/newyork.jpg" alt="New York" style="width:100%" class="w3-hover-opacity">
                    <div class="w3-container w3-white">
                      <p><b>New York</b></p>
                      <p class="w3-opacity">Fri 27 Nov 2016</p>
                      <p>Praesent tincidunt sed tellus ut rutrum sed vitae justo.</p>
                      <button class="w3-button w3-black w3-margin-bottom" 
                      onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
                    </div>
                  </div>
                  
                  <div class="w3-third w3-margin-bottom">
                    <img src="https://www.w3schools.com/w3images/paris.jpg" alt="Paris" style="width:100%" class="w3-hover-opacity">
                    <div class="w3-container w3-white">
                      <p><b>Paris</b></p>
                      <p class="w3-opacity">Sat 28 Nov 2016</p>
                      <p>Praesent tincidunt sed tellus ut rutrum sed vitae justo.</p>
                      <button class="w3-button w3-black w3-margin-bottom" 
                      onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
                    </div>
                  </div>
                  
                  <div class="w3-third w3-margin-bottom">
                    <img src="https://www.w3schools.com/w3images/sanfran.jpg" alt="San Francisco" style="width:100%" class="w3-hover-opacity">
                    <div class="w3-container w3-white">
                      <p><b>San Francisco</b></p>
                      <p class="w3-opacity">Sun 29 Nov 2016</p>
                      <p>Praesent tincidunt sed tellus ut rutrum sed vitae justo.</p>
                      <button class="w3-button w3-black w3-margin-bottom" 
                      onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        
          <!-- Футер с контактами -->
          <footer class="w3-container w3-padding-64 w3-center w3-opacity w3-light-grey w3-xlarge">
            <i class="fa fa-facebook-official w3-hover-opacity"></i>
            <i class="fa fa-instagram w3-hover-opacity"></i>
            <i class="fa fa-twitter w3-hover-opacity"></i>    
            <p class="w3-medium"><i>Powered by PrincessTina</i></p>
          </footer>
        </div>
        `);
        },
    });
});