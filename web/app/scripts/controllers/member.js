app
  .controller('MemberCtrl', [
    '$scope',
    '$http',
    '$location',
    'MemberFactory',
    'AuthenFactory',
    'COMMON_CONST',
    function ($scope,
              $http,
              $location,
              MemberFactory,
              AuthenFactory,
              COMMON_CONST) {

      $scope.member = {};

      // Logout
      $scope.logout = function () {
        $http({
          method: 'POST',
          url: '/member/logout',
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
          .success(function (data) {
            var result = angular.fromJson(data);
            if (!angular.isUndefined(result.success) && result.success) {
              $location.path('/login');
            } else {
              $scope.message = COMMON_CONST.CONNECTION_ERROR;
            }
          })
          .error(function (error) {
            $scope.message = COMMON_CONST.CONNECTION_ERROR;
          });
      };

      /**
       * Get Current Member Information
       */
      $scope.currentMember = function(){
        MemberFactory.getCurrentMember()
          .success(function (data) {
            var result = angular.fromJson(data);
            if (!angular.isUndefined(result.success) && result.success) {
              MemberFactory.set(result.data);
              $scope.member = MemberFactory.get();
            } else {
              $location.path('/login');
            }
          })
          .error(function (error, status) {
            AuthenFactory.isAuthenticate(status);
          });
      };

      $scope.currentMember();
    }]);
