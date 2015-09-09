app
  .controller('CommentCtrl', [
    '$scope',
    '$rootScope',
    '$http',
    '$location',
    '$filter',
    '$sce',
    'COMMON_CONST',
    'AuthenFactory',
    'CommentFactory',
    'MemberFactory',
    function ($scope,
              $rootScope,
              $http,
              $location,
              $filter,
              $sce,
              COMMON_CONST,
              AuthenFactory,
              CommentFactory,
              MemberFactory) {
      $scope.message = "";

      // Show / Hide new Post
      $scope.newComment = false;
      $scope.error = false;

      // Show comment form
      $scope.showHideForm = function () {
        $scope.member = MemberFactory.get();
        $scope.newComment = ($scope.newComment) ? false : true;
        $scope.resetForm();
      };
      /**
       * Clear all form data
       */
      $scope.resetForm = function () {
        $scope.body = null;
        $scope.error = false;
        $scope.message = "";
      };

      // Create New Comment
      $scope.createComment = function (isValid) {
        $scope.error = true;
        $scope.message = "";
        if (isValid) {
          var param = {
            'name': $scope.member.name,
            'email': $scope.member.email,
            'body': $scope.body.replace(/\r?\n/g, '<br />'),
            'created': $filter('date')(new Date(), 'yyyy-MM-dd HH:mm')
          };

          CommentFactory.create(param)
            .success(function (data) {
              var result = angular.fromJson(data);
              if (result.success) {
                $scope.newComment = false;
                $scope.message = "";
                $scope.comments.unshift(param);
              } else {
                $scope.message = COMMON_CONST.CONNECTION_ERROR;
              }
            })
            .error(function (error, status) {
              AuthenFactory.isAuthenticate(status);
              $scope.message = COMMON_CONST.CONNECTION_ERROR;
            });
        }
      };
    }]);
