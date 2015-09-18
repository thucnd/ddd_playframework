app
  .factory('CommentFactory', ['$http', function ($http) {
    var urlBase = '/comment';

    return {
      dl: function (page) {
        return $http({
          method: 'GET',
          url: urlBase + '/list',
          params: {
            'page': page
          }
        });
      },
      view: function (commentId) {
        return $http({
          method: 'GET',
          url: urlBase + '/detail',
          params: {
            'commentId': commentId
          }
        });
      },
      create: function (param) {
        return $http({
          method: 'POST',
          url: urlBase + '/create',
          data: $.param(param),
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
      }
    }
  }]);
