document.addEventListener("DOMContentLoaded", () => {
  const slider = document.querySelector("[data-slider]");
  if (!slider) return;

  const track  = slider.querySelector("[data-slider-track]");
  const slides = Array.from(track.children);
  const totalSlides = slides.length;

  let visibleCount;
  let slideWidthPercent;
  let centerOffset;
  let minIndex;
  let maxIndex;
  let currentIndex = 0;

  function computeLayout() {
    const isMobile = window.innerWidth <= 480;

    visibleCount = isMobile ? 1 : 5;
    slideWidthPercent = 100 / visibleCount;
    centerOffset = Math.floor(visibleCount / 2);

    if (visibleCount === 1) {
      minIndex = 0;
      maxIndex = totalSlides - 1;
    } else {
      minIndex = centerOffset;
      maxIndex = totalSlides - centerOffset - 1;
    }

    if (currentIndex < minIndex || currentIndex > maxIndex) {
      currentIndex = minIndex;
    }

    updateSlider();
  }

  function updateSlider() {
    slides.forEach((slide, i) => {
      slide.classList.toggle("is-active", i === currentIndex);
    });

    const offsetIndex = currentIndex - centerOffset;
    const offset = offsetIndex * slideWidthPercent;
    track.style.transform = `translateX(-${offset}%)`;
  }

  function goNext() {
    if (currentIndex >= maxIndex) {
      currentIndex = minIndex;
    } else {
      currentIndex++;
    }
    updateSlider();
  }

  let timer = setInterval(goNext, 5000);

  window.addEventListener("resize", computeLayout);

  computeLayout();
});
