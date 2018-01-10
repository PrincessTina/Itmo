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
                    this.paint();
                },
                fail: () => {
                    throw "Error in getting news";
                }
            });
        },

        paint() {
            for (let i = 0; i < 3; i++) {
                let note = this.collection.models[i];

                document.getElementsByClassName("noteBlock")[0].innerHTML += `
                <div class="w3-third w3-margin-bottom">
                    <img src="${note.attributes.image.link}" style="width:100%" class="w3-hover-opacity">
                    <div class="w3-container w3-white">
                      <p class="w3-opacity">${note.attributes.date}</p>
                      <p>${note.attributes.description}</p>
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