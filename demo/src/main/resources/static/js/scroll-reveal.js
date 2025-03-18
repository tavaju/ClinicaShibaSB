document.addEventListener('DOMContentLoaded', function () {
    const scrollElements = document.querySelectorAll('.scroll-reveal');

    const elementInView = (el, dividend = 1) => {
        const elementTop = el.getBoundingClientRect().top;
        return (
            elementTop <= 
            (window.innerHeight || document.documentElement.clientHeight) / dividend
        );
    };

    const displayScrollElement = (element) => {
        element.classList.add('visible');
    };

    const hideScrollElement = (element) => {
        element.classList.remove('visible');
    };

    const handleScrollAnimation = () => {
        scrollElements.forEach((el) => {
            if (elementInView(el, 1.25)) {
                displayScrollElement(el);
            } else {
                hideScrollElement(el);
            }
        });
    };

    // Immediately display the hero section with slide-up effect
    const heroSection = document.querySelector('.blog-hero');
    if (heroSection) {
        heroSection.classList.add('slide-up');
        setTimeout(() => {
            heroSection.classList.add('visible');
        }, 100); // Delay to trigger the transition
    }

    window.addEventListener('scroll', () => {
        handleScrollAnimation();
    });
});
