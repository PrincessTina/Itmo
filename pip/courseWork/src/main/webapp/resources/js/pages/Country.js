$(document).ready(() => {
    var CountryModel = Backbone.Model.extend({
        url:  'countries?id=' + window.location.href.charAt(window.location.href.length - 1),
    });

    window.Country = Backbone.View.extend({
        el: $('body'),
        menu: null,

        events: {
            'click ._myVkPage': 'goToMyVkPage',
            'keyup #search': 'search',
        },

        initialize() {
            _.bindAll(this, 'render');

            this.country = new CountryModel();
            this.render();

            this.menu = new Menu();

            this.country.fetch({
                success: () => {
                    this.paste();
                },
                fail: () => {
                    throw "Error in getting news";
                }
            });
        },

        paste() {
            let name = this.country.attributes.name;
            let description = this.country.attributes.description;
            let image1 = this.country.attributes.images[0];
            let image2 = this.country.attributes.images[1];
            let verticalImage;
            let horizontalImage;

            if (image1.id < image2.id) {
                horizontalImage = image1.link;
                verticalImage = image2.link;
            } else {
                horizontalImage = image2.link;
                verticalImage = image1.link;
            }


            document.getElementsByClassName("_name")[0].innerHTML = name;
            document.getElementsByClassName("_description")[0].innerHTML = description;
            document.getElementsByClassName("verticalImage")[0].src = verticalImage;
            document.getElementsByClassName("horizontalImage")[0].src = horizontalImage;
        },

        search() {
            let input = document.getElementById("search");
            let filter = input.value.toUpperCase();
            let ul = document.getElementById("myUL");
            let li = ul.getElementsByTagName("li");

            for (let i = 0; i < li.length; i++) {
                if (li[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
                    li[i].style.display = "";
                } else {
                    li[i].style.display = "none";
                }
            }
        },

        goToMyVkPage() {
            window.open("https://vk.com/kristi_tulpan");
        },

        render() {
            $(this.el).append(`
        <div>
          <!-- Меню -->
          <div class="menu"></div>
          
          <header class="w3-display-container w3-content w3-wide" style="max-width:1600px;min-width:500px" id="home">
              <img class="w3-image horizontalImage" alt="Hamburger Catering" width="1600" height="800">
              <div class="w3-display-bottomleft w3-padding-large w3-opacity">
                <h1 class="w3-text-white w3-xxlarge _name"></h1>
              </div>
          </header>
          
          <div class="w3-content" style="max-width:1100px">
              <div class="w3-row w3-padding-64" id="about">
                <div class="w3-col m6 w3-padding-large w3-hide-small">
                 <img class="w3-round w3-image w3-opacity-min w3-hover-opacity-off verticalImage" 
                 alt="Table Setting" width="600" height="750">
                </div>
            
                <div class="w3-col m6 w3-padding-large">
                  <h1 class="w3-center">About</h1><br>
                  <p class="w3-large _description"></p>
                </div>
              </div>
          </div>
          
          <div class="w3-container">
            <h2 class="w3-center">Legends</h2>
            <input class="w3-input w3-border-black w3-margin w3-round w3-padding" type="text" placeholder="Search.." id="search">
            <ul class="w3-ul w3-margin-top" id="myUL">
                <li>Adele</li>
                <li style="">Agnes</li>
                <li style="">Billy</li>
                <li style="">Bob</li>
                <li style="">Calvin</li>
                <li style="">Christina</li>
                <li>Cindy</li>
            </ul>
          </div>
        
          <!-- Футер с контактами -->
          <footer class="w3-container w3-padding-64 w3-center w3-opacity w3-light-grey w3-xlarge">
            <i class="fa fa-vk w3-hover-opacity _myVkPage"></i>   
            <p class="w3-medium"><i>Powered by PrincessTina</i></p>
          </footer>
        </div>
        `);
        },
    });
});