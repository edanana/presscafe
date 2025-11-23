// GALLERY SLIDER – full width, auto-loop, center highlight
document.addEventListener("DOMContentLoaded", () => {
  const slider = document.querySelector("[data-slider]");
  if (!slider) return;

  const track  = slider.querySelector("[data-slider-track]");
  const slides = Array.from(track.children);

  const totalSlides = slides.length;
  let slideWidthPercent = window.innerWidth <= 480 ? 100 : 33.3333;
  let currentIndex = window.innerWidth <= 480 ? 0 : 1;

  function updateSlider() {
    slides.forEach((slide, i) => {
      slide.classList.toggle("is-active", i === currentIndex);
    });

    const offset = currentIndex * slideWidthPercent;
    track.style.transform = `translateX(-${offset}%)`;
  }

  function goNext() {
    currentIndex = (currentIndex + 1) % totalSlides;
    updateSlider();
  }

  let timer = setInterval(goNext, 8000);

  slider.addEventListener("mouseenter", () => {
    clearInterval(timer);
  });

  slider.addEventListener("mouseleave", () => {
    timer = setInterval(goNext, 8000);
  });

  window.addEventListener("resize", () => {
    const isMobile = window.innerWidth <= 480;
    slideWidthPercent = isMobile ? 100 : 33.3333;

    if (isMobile && currentIndex === 1) {
      currentIndex = 0;
    }

    updateSlider();
  });

  updateSlider();
});
