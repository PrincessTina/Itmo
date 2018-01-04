$(document).ready(() => {
    window.ImagesCarousel = Backbone.View.extend({
        el: null,
        carouselImageIndex: 0,

        initialize() {
            this.el = $('.images_carousel');

            _.bindAll(this, 'render');
            _.bindAll(this, 'changeCarouselImage');

            this.collection = new ImagesCollection();
            this.collection.fetch({
                success: () => {
                    this.render();
                    this.changeCarouselImage();
                },
                fail: () => {
                    throw "Error in imagesCarousel";
                }
            });
        },

        /**
         * Меняет картинку в карусели
         */
        changeCarouselImage() {
            let slideImages = document.getElementsByClassName("mySlides");

            // Убираем все
            for (let i = 0; i < slideImages.length; i++) {
                slideImages[i].style.display = "none";
            }

            // Проставляем carouselImageIndex, чтобы знать, какой нужно показать
            this.carouselImageIndex++;
            if (this.carouselImageIndex > slideImages.length - 1) {
                this.carouselImageIndex = 0
            }

            // Показываем нужный слайд
            slideImages[this.carouselImageIndex].style.display = "block";

            // Поставим вызов этой же функции через 4 секунды
            setTimeout(this.changeCarouselImage, 4 * 1000);
        },

        render() {
            let imagesHTML = '';

            this.collection.models.forEach((image) => {
                imagesHTML += ` <div class="mySlides w3-display-container w3-center">
                                  <img src="${image.attributes.link}" style="width:100%">
                                </div>`;
            });

            $(this.el).html(imagesHTML);
        }
    });
});