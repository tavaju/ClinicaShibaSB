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

    // Immediately display the hero section and its children
    const heroSection = document.querySelector('.blog-hero');
    if (heroSection) {
        heroSection.classList.add('visible');
        const heroChildren = heroSection.querySelectorAll('.scroll-reveal');
        heroChildren.forEach((child) => {
            child.classList.add('visible');
        });
    }

    window.addEventListener('scroll', () => {
        handleScrollAnimation();
    });
});
