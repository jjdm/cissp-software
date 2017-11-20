var app = angular.module('cissp', []);

app.controller('ListController', function ($scope, $log) {

	$scope.maxId = 2;
	$scope.name = null;
	$scope.description = null;

	$scope.items = [
		{"id": 1, "name": "Network Event", "description": "Bandwidth anomoly detected.  Potential DDOS."},
		{"id": 2, "name": "Sensor Alert", "description": "SNORT sensor flagged known IP."}
	]

	$scope.addItem = function () {
		$scope.maxId++;
		$log.debug("Adding item " + $scope.maxId);
		$scope.items.push({
			"id": $scope.maxId,
			"name": $scope.name,
			"description": $scope.description
		});
		$scope.name = null;
		$scope.description = null;
	}

});