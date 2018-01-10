$(document).ready(() => {
    var NoteModel = Backbone.Model.extend();

    var NoteCollection = Backbone.Collection.extend({
        model: NoteModel,
        url: 'notes'
    });

    window.News = Backbone.View.extend({
        el: $('body'),
        menu: null,

        events: {
            'click ._myVkPage': 'goToMyVkPage'
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'paint');

            this.collection = new NoteCollection();
            this.render();

            this.menu = new Menu();

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
            this.collection.models.forEach((note) => {
                if (document.getElementsByClassName("newsBlock")[0].children.length < 4) {
                    document.getElementsByClassName("newsBlock")[0].innerHTML += `
                    <div class="w3-quarter">
                      <img class="w3-hover-grayscale" src="${note.attributes.image.link}" alt="Sandwich" style="width:100%">
                      <h3>${note.attributes.date}</h3>
                      <p>${note.attributes.description}</p>
                    </div>`;
                } else {
                    document.getElementsByClassName("newsBlock")[0].classList.remove("newsBlock");

                    document.getElementsByClassName("w3-main")[0].innerHTML += `
                    <div class="w3-row-padding w3-padding-16 w3-center newsBlock"></div>`;

                    document.getElementsByClassName("newsBlock")[0].innerHTML += `
                    <div class="w3-quarter">
                      <img class="w3-hover-grayscale" src="${note.attributes.image.link}" alt="Sandwich" style="width:100%">
                      <h3>${note.attributes.date}</h3>
                      <p>${note.attributes.description}</p>
                    </div>`
                }});
        },

        goToMyVkPage() {
            window.open("https://vk.com/kristi_tulpan");
        },

        render() {
            $(this.el).append(`
         <!-- Меню -->
         <div class="menu"></div>
         
         <!-- Новости -->
         <div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px">
              <h1 class="w3-center">Новости</h1>
              <div class="w3-row-padding w3-padding-16 w3-center newsBlock"></div>
        </div>
        
        <!-- Футер с контактами -->
          <footer class="w3-container w3-padding-64 w3-center w3-opacity w3-light-grey w3-xlarge">
            <i class="fa fa-vk w3-hover-opacity _myVkPage"></i>   
            <p class="w3-medium"><i>Powered by PrincessTina</i></p>
          </footer>
        `);
        },
    });
});