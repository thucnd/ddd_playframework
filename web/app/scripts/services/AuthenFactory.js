app
  .constant('AUTHEN_CONST', {
    UNAUTHORIZED: 401
  })
  .factory('AuthenFactory', [
    '$http',
    '$location',
    'AUTHEN_CONST',
    function ($http,
              $location,
              AUTHEN_CONST) {
      return {
        isAuthenticate: function (status) {
          if (status === AUTHEN_CONST.UNAUTHORIZED) {
            return $location.path('/login');
          }
        }
      };
    }]);
