document.addEventListener("DOMContentLoaded", () => {
  const slider = document.querySelector("[data-slider]");
  if (!slider) return;

  const track   = slider.querySelector("[data-slider-track]");
  const slides  = Array.from(track.children);
  const prevBtn = slider.querySelector("[data-slider-prev]");
  const nextBtn = slider.querySelector("[data-slider-next]");

  let currentIndex = 1; // start with middle slide on desktop
  let slideWidthPercent = window.innerWidth <= 360 ? 100 : 33.3333;

  function updateSlider() {
    // active state
    slides.forEach((slide, i) => {
      slide.classList.toggle("is-active", i === currentIndex);
    });

    const offset = currentIndex * slideWidthPercent;
    track.style.transform = `translateX(-${offset}%)`;
  }

  prevBtn.addEventListener("click", () => {
    currentIndex = (currentIndex - 1 + slides.length) % slides.length;
    updateSlider();
  });

  nextBtn.addEventListener("click", () => {
    currentIndex = (currentIndex + 1) % slides.length;
    updateSlider();
  });

  // Recalculate when screen size changes (e.g. rotate / resize)
  window.addEventListener("resize", () => {
    slideWidthPercent = window.innerWidth <= 360 ? 100 : 33.3333;

    // on very small screens, start from first slide instead of middle
    if (window.innerWidth <= 360 && currentIndex === 1) {
      currentIndex = 0;
    }

    updateSlider();
  });

  // initial state
  updateSlider();
});