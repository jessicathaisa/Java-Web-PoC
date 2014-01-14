///<reference path='../../reference.ts'/>

module service.impl {

    export class StudentServiceImpl extends service.impl.base.AbstractCrudServiceImpl<entity.Student>
        implements service.contract.StudentServiceContract {

		static $inject = ['$http'];

        constructor($http: ng.IHttpService) {
            super($http, "student");
        }
    }
}
