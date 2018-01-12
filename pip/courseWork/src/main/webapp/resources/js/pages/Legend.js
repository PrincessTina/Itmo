$(document).ready(() => {
    var LegendModel = Backbone.Model.extend({
        url:  'legends?id=' + window.location.href.substr(window.location.href.lastIndexOf("#") + 1),
    });

    window.Legend = Backbone.View.extend({
        el: $('body'),
        menu: null,

        events: {
            'click ._myVkPage': 'goToMyVkPage',
        },

        initialize() {
            _.bindAll(this, 'render');

            this.legend = new LegendModel();
            this.render();

            this.menu = new Menu();

            this.legend.fetch({
                success: () => {
                    this.paste();
                },
                fail: () => {
                    throw "Error in getting news";
                }
            });
        },

        paste() {
            let name = this.legend.attributes.name.toUpperCase();
            let description = this.legend.attributes.description;
            let link = this.legend.attributes.image.link;
            let country_id = this.legend.attributes.country_id;

            document.getElementsByClassName("_name")[0].innerHTML = "❝" + name + "❞";
            document.getElementsByClassName("_description")[0].innerHTML = description;

            if (country_id === 1) {
                document.getElementById("myGrid").innerHTML = `
                    <div class="w3-half">
                    <img class="_image w3-opacity w3-hover-opacity-off" style="width:100%">
                    <img src="http://passport.777ru.ru/sites/default/files/images/Greece_ancient_capital_of_antiquity_00.jpg" style="width:100%">
                    <img src="https://i.pinimg.com/736x/f2/ab/1c/f2ab1cfb20f1f96826b13d10637dfa9e--greece-vacation-greece-trip.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://clubtravel39.ru/wp-content/uploads/2017/03/tP6toVHnWJk.jpg" style="width:100%">
                  </div>
                
                  <div class="w3-half">
                    <img src="https://majbutne.com.ua/wp-content/uploads/2016/03/GreeceOM_15.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://bigpicture.ru/wp-content/uploads/2013/06/Places05.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://wallpaperscraft.ru/image/gorod_afiny_parfenon_dostoprimechatelnost_greciya_58007_1024x768.jpg" style="width:100%">
                    <img src="https://img1.goodfon.ru/original/1920x1280/1/fa/greciya-santorini-nebo-oblaka.jpg" style="width:100%">
                  </div>
                `;
            } else if (country_id === 2) {
                document.getElementById("myGrid").innerHTML = `
                    <div class="w3-half">
                    <img class="_image" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://ic.pics.livejournal.com/maritana/53815695/5693303/5693303_original.jpg" style="width:100%">
                    <img src="https://tcc.com.ua/storage/gallery/images/hotels/4bf/4bf9078d3895c6a09f0130c0927d577c.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="https://img3.goodfon.ru/original/1280x720/c/5f/rome-italy-forum-romanum.jpg" style="width:100%">
                  </div>
                
                  <div class="w3-half">
                    <img class="w3-opacity w3-hover-opacity-off" src="https://w-club.com.ua/images/blog/Italy_turu.jpg" style="width:100%">
                    <img src="http://www.zastavki.com/pictures/originals/2017World___Italy_Castle_of_St._Angel_on_the_water__Rome._Italy_114203_.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://img.crazys.info/files/i/2013.2.8/thumbs/1360302607_k-35.jpg" style="width:100%">
                    <img src="http://ee24.ru/media/cache/a8/f0/a8f07183aabe6c7ff15b416a67f229a7.jpg" style="width:100%">
                  </div>
                `;
            } else if (country_id === 3) {
                document.getElementById("myGrid").innerHTML = `
                    <div class="w3-half">
                    <img class="_image" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://mtdata.ru/u2/photoBF69/20665800453-0/original.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="https://notagram.ru/wp-content/uploads/2017/01/7fbd8801b9950beec7b351d6521f385e.jpg" style="width:100%">
                    <img src="https://cs6.livemaster.ru/storage/70/11/0271a9f298c8d96add6cae3ed1fp.jpg" style="width:100%">
                  </div>
                
                  <div class="w3-half">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://www.abrisburo.ru/architecture/rus/21.jpg" style="width:100%">
                    <img src="https://cyrillitsa.ru/wp-content/uploads/2017/03/Viktor_Vasnetsov_-_Bogatyri_-_Google_Art_Project-1.jpg" style="width:100%">
                    <img src="https://img2.goodfon.ru/original/2880x1800/c/4a/zima-priroda-reka-derevya.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://mtdata.ru/u22/photo58AD/20765421832-0/original.jpg" style="width:100%">
                  </div>
                `;
            } else if (country_id === 4) {
                document.getElementById("myGrid").innerHTML = `
                    <div class="w3-half">
                    <img class="_image w3-opacity w3-hover-opacity-off" style="width:100%">
                    <img src="http://cdn.slowrobot.com/63201718473428513.jpg" style="width:100%">
                    <img src="http://www.ancient-origins.net/sites/default/files/field/image/Nitocris-pharaoh-egypt.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://altustour.by/image/2/1180/612/5/multilanding/img/nuvejba-port-i-kurort-egipta_4.jpg" style="width:100%">
                  </div>
                
                  <div class="w3-half">
                    <img src="https://pp.vk.me/c636517/v636517718/4909d/TlCOqlj9T3Y.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="https://s1.1zoom.ru/b5253/437/Egypt_Desert_Rivers_Stones_Cairo_Pyramid_523000_1920x1080.jpg" style="width:100%">
                    <img class="w3-opacity w3-hover-opacity-off" src="http://life.pasko.com.ua/wp-content/uploads/2017/07/527cb5543e7ad.jpg" style="width:100%">
                    <img src="http://укроп.org/wp-content/uploads/2015/11/%D0%B5%D0%B3%D0%B8%D0%BF%D0%B5%D1%82.jpg" style="width:100%">
                  </div>
                `;
            }

            document.getElementsByClassName("_image")[0].src = link;
        },

        goToMyVkPage() {
            window.open("https://vk.com/kristi_tulpan");
        },

        render() {
            $(this.el).append(`
        <div>
          <!-- Меню -->
          <div class="menu"></div>
          
          <div class="w3-content" style="max-width:1500px">
            <!-- Header -->
            <div class="w3-opacity" style="margin-top: 10%">
                <header class="w3-center w3-margin-bottom">
                  <h1><b class="_name"></b></h1>
                </header>
            </div>
            
            <!-- Photo Grid -->
            <div class="w3-row" id="myGrid" style="margin-bottom:128px"></div>
            
            <!-- Футер с лайками -->
            <footer class="w3-container w3-padding-64 w3-right w3-opacity w3-pink w3-large">
              <p class="w3-medium w3-text-black">19</p>
              <i class="fa fa-heart w3-hover-grayscale w3-text-black"></i>   
            </footer>
            
            <!-- Legend description -->
            <div class="w3-container w3-dark-grey w3-center w3-text-light-grey w3-padding-32" id="about">
                <div class="w3-content w3-justify" style="max-width:600px">
                  <p class="_description"></p>
                  <hr class="w3-opacity">
                </div>
            </div>
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