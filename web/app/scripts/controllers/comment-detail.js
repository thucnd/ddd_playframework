app
  .controller('CommentDetailCtrl', [
    '$scope',
    '$http',
    '$sce',
    '$routeParams',
    'blockUI',
    'COMMON_CONST',
    'AuthenFactory',
    'CommentFactory',
    'MemberFactory',
    function ($scope,
              $http,
              $sce,
              $routeParams,
              blockUI,
              COMMON_CONST,
              AuthenFactory,
              CommentFactory,
              MemberFactory) {
      $scope.message = "";
      $scope.member = MemberFactory.get();
      $scope.comment = {};

      // Display Html
      $scope.displayHtml = function (text) {
        return $sce.trustAsHtml(text);
      }

      /**
       * check Login information
       */
      $scope.view = function () {

        CommentFactory.view($routeParams.commentId)
          .success(function (data) {
            var result = angular.fromJson(data);

            if (result.success) {
              $scope.comment = result.data;
            }
          })
          .error(function (error, status) {
            AuthenFactory.isAuthenticate(status);
          });
      };


      $scope.startUp = function(){
        $scope.view();
      }

      $scope.startUp();
    }]);
