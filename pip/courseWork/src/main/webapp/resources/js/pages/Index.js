$(document).ready(() => {
    var NoteModel = Backbone.Model.extend();

    var NoteCollection = Backbone.Collection.extend({
        model: NoteModel,
        url: 'notes'
    });

    window.Index = Backbone.View.extend({
        el: $('body'),
        slider: null,
        menu: null,

        events: {
          'click ._myVkPage': 'goToMyVkPage'
        },

        initialize() {
            _.bindAll(this, 'render');

            this.collection = new NoteCollection();
            this.render();

            this.menu = new Menu();
            this.slider = new IndexSlider();

            this.collection.fetch({
                success: () => {
                    this.paste();
                },
                fail: () => {
                    throw "Error in getting news";
                }
            });
        },

        paste() {
            for (let i = 0; i < 3; i++) {
                let note = this.collection.models[i];

                document.getElementsByClassName("noteBlock")[0].innerHTML += `
                <div class="w3-third w3-margin-bottom">
                    <img src="${note.attributes.image.link}" style="width:100%" class="w3-hover-opacity">
                    <div class="w3-container w3-white">
                      <p class="w3-opacity">${note.attributes.date}</p>
                      <p><i>${note.attributes.description}</i></p>
                    </div>
                  </div>
                `;
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
          
          <div class="w3-content" style="max-width:2000px;margin-top:46px">
            <!-- Слайдер -->
            <div class="slider"></div>
            
            <!-- Описание -->
            <div class="w3-container w3-content w3-center w3-padding-64" style="max-width:800px" id="band">
              <h2 class="w3-wide">Mythology of different countries</h2>
              <p class="w3-opacity"><i>We love stories</i></p>
              <p class="w3-justify">
               <br/>
               This resource gives a possibly to play a game, to read and evaluate myths and legends presented on the 
               website, pass a short test which will give us the opportunity to tell you stories of interest.
              </p>
            </div>
            
            <!-- Новости -->
            <div class="w3-black" id="tour">
              <div class="w3-container w3-content w3-padding-64" style="max-width:800px">
                <h2 class="w3-wide w3-center">News</h2>
                <p class="w3-opacity w3-center"></p><br>
                <div class="w3-row-padding w3-padding-32 noteBlock" style="margin:0 -16px"> </div>
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