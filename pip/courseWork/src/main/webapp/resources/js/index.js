$(document).ready(function () {
    window.Index = Backbone.View.extend({
        el: $('body'),
        carouselImageIndex: 1,

        initialize: function () {
            _.bindAll(this, 'render');
            _.bindAll(this, 'changeCarouselImage');

            this.render();
            this.changeCarouselImage();
        },

        changeCarouselImage: function() {
            let slideElements = document.getElementsByClassName("mySlides");

            // Убираем все
            for (let i = 0; i < slideElements.length; i++) {
                slideElements[i].style.display = "none";
            }

            // Проставляем carouselImageIndex, чтобы знать, какой нужно показать
            this.carouselImageIndex++;
            if (this.carouselImageIndex > slideElements.length) {
                this.carouselImageIndex = 1
            }

            // Показываем нужный слайд
            slideElements[this.carouselImageIndex-1].style.display = "block";

            // Поставим вызов этой же функции через 4 секунды
            setTimeout(this.changeCarouselImage, 4 * 1000);
        },

        render: function () {
            $(this.el).append(`
<div>

  <!-- Навигация сверху -->
  <div class="w3-top">
    <div class="w3-bar w3-black w3-card">
      <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
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
      
      <a href="#contact" class="w3-bar-item w3-button w3-padding-large w3-hide-small">ТОП-10</a>
      <a href="#contact" class="w3-bar-item w3-button w3-padding-large w3-hide-small">НОВОСТИ</a>
      <div class="w3-bar-item w3-button w3-padding-large w3-hide-small w3-right" onclick="new RegWindow();">РЕГИСТРАЦИЯ</div>
    </div>
  </div>
    
  <!-- Новости -->
  <div class="w3-content" style="max-width:2000px;margin-top:46px">
    <div class="mySlides w3-display-container w3-center">
      <img src="https://www.w3schools.com/w3images/la.jpg" style="width:100%">
    </div>
   
    <div class="mySlides w3-display-container w3-center">
      <img src="https://www.w3schools.com/w3images/ny.jpg" style="width:100%">
    </div>
    
    <div class="mySlides w3-display-container w3-center">
      <img src="https://www.w3schools.com/w3images/chicago.jpg" style="width:100%">
    </div>
  
    <div class="w3-container w3-content w3-center w3-padding-64" style="max-width:800px" id="band">
      <h2 class="w3-wide">САЙТ ЛЕГЕНД</h2>
      <p class="w3-opacity"><i>Мы лююбим истории</i></p>
      <p class="w3-justify">
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.Многотекста.
      </p>
    </div>
    
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
              <button class="w3-button w3-black w3-margin-bottom" onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
            </div>
          </div>
          
          <div class="w3-third w3-margin-bottom">
            <img src="https://www.w3schools.com/w3images/paris.jpg" alt="Paris" style="width:100%" class="w3-hover-opacity">
            <div class="w3-container w3-white">
              <p><b>Paris</b></p>
              <p class="w3-opacity">Sat 28 Nov 2016</p>
              <p>Praesent tincidunt sed tellus ut rutrum sed vitae justo.</p>
              <button class="w3-button w3-black w3-margin-bottom" onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
            </div>
          </div>
          
          <div class="w3-third w3-margin-bottom">
            <img src="https://www.w3schools.com/w3images/sanfran.jpg" alt="San Francisco" style="width:100%" class="w3-hover-opacity">
            <div class="w3-container w3-white">
              <p><b>San Francisco</b></p>
              <p class="w3-opacity">Sun 29 Nov 2016</p>
              <p>Praesent tincidunt sed tellus ut rutrum sed vitae justo.</p>
              <button class="w3-button w3-black w3-margin-bottom" onclick="document.getElementById('ticketModal').style.display='block'">Buy Tickets</button>
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
    <p class="w3-medium">Powered by PrincessTina</p>
  </footer>

</div>
      `);
      }
  });
});