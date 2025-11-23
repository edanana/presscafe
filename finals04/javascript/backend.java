document.addEventListener("DOMContentLoaded", () => {
  const slider = document.querySelector("[data-slider]");
  if (!slider) return;

  const track  = slider.querySelector("[data-slider-track]");
  const slides = Array.from(track.children);

  const AUTOPLAY_DELAY = 5000;

  let slidesPerView   = window.innerWidth <= 480 ? 1 : 3;
  let slideWidthPercent = 100 / slidesPerView;
  let centerOffset      = Math.floor(slidesPerView / 2);

  let currentIndex = centerOffset;

  function updateSlider() {
    slides.forEach((slide, i) => {
      slide.classList.toggle("is-active", i === currentIndex);
    });

    const offset = (currentIndex - centerOffset) * slideWidthPercent;
    track.style.transform = `translateX(-${offset}%)`;
  }

  function nextSlide() {
    currentIndex = (currentIndex + 1) % slides.length;
    updateSlider();
  }

  let autoplayId = setInterval(nextSlide, AUTOPLAY_DELAY);

  slider.addEventListener("mouseenter", () => clearInterval(autoplayId));
  slider.addEventListener("mouseleave", () => {
    autoplayId = setInterval(nextSlide, AUTOPLAY_DELAY);
  });

  window.addEventListener("resize", () => {
    slidesPerView     = window.innerWidth <= 480 ? 1 : 3;
    slideWidthPercent = 100 / slidesPerView;
    centerOffset      = Math.floor(slidesPerView / 2);

    currentIndex = Math.min(currentIndex, slides.length - 1);
    updateSlider();
  });

  updateSlider();
});