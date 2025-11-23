// GALLERY SLIDER – 5-wide wheel on desktop, 1-wide on mobile
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

    // 5 visible on desktop, 1 on mobile
    visibleCount = isMobile ? 1 : 5;
    slideWidthPercent = 100 / visibleCount;
    centerOffset = Math.floor(visibleCount / 2);

    if (visibleCount === 1) {
      // mobile: you can show any index
      minIndex = 0;
      maxIndex = totalSlides - 1;
    } else {
      // desktop: keep at least 2 slides before and after active
      minIndex = centerOffset;                       // e.g. 2
      maxIndex = totalSlides - centerOffset - 1;     // e.g. T-3
    }

    // clamp current index into valid range
    if (currentIndex < minIndex || currentIndex > maxIndex) {
      currentIndex = minIndex;
    }

    updateSlider();
  }

  function updateSlider() {
    // highlight the active slide
    slides.forEach((slide, i) => {
      slide.classList.toggle("is-active", i === currentIndex);
    });

    // move track so active slide stays in the center
    const offsetIndex = currentIndex - centerOffset;
    const offset = offsetIndex * slideWidthPercent;
    track.style.transform = `translateX(-${offset}%)`;
  }

  function goNext() {
    if (currentIndex >= maxIndex) {
      currentIndex = minIndex;   // loop back but keep center logic
    } else {
      currentIndex++;
    }
    updateSlider();
  }

  // autoplay every 8 seconds
  let timer = setInterval(goNext, 8000);

  // pause on hover (desktop)
  slider.addEventListener("mouseenter", () => {
    clearInterval(timer);
  });

  slider.addEventListener("mouseleave", () => {
    timer = setInterval(goNext, 8000);
  });

  // recompute layout on resize (desktop ↔ mobile)
  window.addEventListener("resize", computeLayout);

  // initial layout
  computeLayout();
});
