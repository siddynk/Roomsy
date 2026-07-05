/* Roomsy theme toggle — manual light/dark switch, persisted
   in localStorage. Call applyStoredTheme() as early as possible
   (in <head>, inline) to avoid a flash of the wrong theme. */

(function () {
  window.ROOMSY_THEME_KEY = 'roomsy-theme';

  window.applyStoredTheme = function () {
    var saved = localStorage.getItem(window.ROOMSY_THEME_KEY);
    if (saved === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark');
    } else {
      document.documentElement.removeAttribute('data-theme');
    }
  };

  window.toggleRoomsyTheme = function () {
    var isDark = document.documentElement.getAttribute('data-theme') === 'dark';
    if (isDark) {
      document.documentElement.removeAttribute('data-theme');
      localStorage.setItem(window.ROOMSY_THEME_KEY, 'light');
    } else {
      document.documentElement.setAttribute('data-theme', 'dark');
      localStorage.setItem(window.ROOMSY_THEME_KEY, 'dark');
    }
  };
})();
