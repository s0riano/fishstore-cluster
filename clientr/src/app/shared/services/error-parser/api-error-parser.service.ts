import axios from 'axios';
export const IsApiError = 'IsApiError';

export class ApiErrorParserService {
  parseHttpError(error: any): ApiErrorResponse {
    const apiError = new ApiErrorResponse();
    apiError.headers = error.headers || {};
    apiError.data = error.response?.data || {};
    apiError.status = error.response?.status || 0;
    apiError.config = {
      url: error.config?.url,
      method: error.config?.method,
      data: error.config?.data,
    };
    apiError.statusText = error.message;
    apiError.hasValidationErrors = false;
    apiError.validationErrors = null;
    apiError.isUserFriendlyError = false;
    apiError.userFriendlyError = undefined;
    apiError.discriminator = IsApiError;

    if (apiError.status === 400) {
      // Handle validation errors
      // ... (adapt as necessary)
      //return this.handleValidationResponse(httpError);
    } else {
      // Handle other types of errors
      // ... (adapt as necessary)
      /*if (httpError.error) {
        this.checkUserFriendlyErrorMessage(httpError.error.errorInfo, apiError);
      }*/
    }

    return apiError;
  }


  private checkUserFriendlyErrorMessage(errorInfo, response: ApiErrorResponse) {
    if (errorInfo) {
      if (errorInfo.isUserFriendlyException) {
        response.isUserFriendlyError = errorInfo.isUserFriendlyException;
        response.userFriendlyError = errorInfo.message;
      }
      if (errorInfo.innerErrorInfo) {
        this.checkUserFriendlyErrorMessage(errorInfo.innerErrorInfo, response);
      }
    }
  }

  private handleValidationResponse(error: any): ApiErrorResponse {
    const apiError = new ApiErrorResponse();

    // Extracting information from Axios error structure
    apiError.headers = this.getHeadersAsKeyValuePairs(error.response?.headers);
    apiError.data = error.response?.data || {};
    apiError.status = error.response?.status || 0;
    apiError.config = {
      url: error.config?.url,
      method: error.config?.method,
      data: error.config?.data,
    };
    apiError.statusText = error.message;
    apiError.hasValidationErrors = false;
    apiError.validationErrors = null;
    apiError.isUserFriendlyError = false;
    apiError.userFriendlyError = undefined;
    apiError.discriminator = IsApiError;

    // Adjusted logic to handle errors based on Axios error structure
    if (typeof apiError.data.isValid === 'boolean') {
      apiError.validationErrors = this.parseFluentValidationResponse(
          apiError.data.errors
      );
    } else if (typeof apiError.data.modelState !== 'undefined') {
      apiError.validationErrors = this.parseModelStateResponse(
          apiError.data.modelState
      );
    }

    apiError.hasValidationErrors = apiError.validationErrors !== null;

    return apiError;
  }

  private parseFluentValidationResponse(errors) {
    const validationErrors: ValidationError[] = [];
    errors.forEach((obj, key) => {
      validationErrors.push({
        key: obj.propertyName,
        errorMessage: obj.errorMessage,
      });
    });
    return validationErrors;
  }

  private parseModelStateResponse(errors: any) {
    const validationErrors: ValidationError[] = [];
    for (const key in errors) {
      const obj = errors[key] as any;
      if (Array.isArray(obj.errors)) {
        obj.errors.forEach(error => {
          validationErrors.push({
            key: key.toString(),
            errorMessage: error.errorMessage,
            value: obj.value,
          });
        });
      } else {
        if (errors.hasOwnProperty(key)) {
          if (errors[key].length) {
            validationErrors.push({
              key: '',
              errorMessage: errors[key][0],
              value: obj.value,
            });
          }
        }
      }
    }
    return validationErrors;
  }

  private getHeadersAsKeyValuePairs(headers: any) { //etterkvart fjerne 'any'?
    const KeyValuePair = {};
    for (const key in headers) {
      if (headers.hasOwnProperty(key)) {
        KeyValuePair[key] = headers[key];
      }
    }
    return KeyValuePair;
  }
}

export interface ErrorHandlerObject {
  data: any;
  status: any;
  headers: any;
  config: any;
  statusText: any;
}

export interface ValidationError {
  key: string;
  errorMessage: string;
  value?: unknown;
}

export class ApiErrorResponse {
  headers: any;
  data: any;
  status: any;
  config: any;
  statusText: any;

  hasValidationErrors: boolean;
  //validationErrors?: ValidationError[];
  validationErrors?: any[] | null; // Making it optional and allowing null


  isUserFriendlyError: boolean;
  //userFriendlyError: string;
  userFriendlyError?: string | undefined; // Making it optional

  discriminator: string;


}

export interface IErrorDisplayModel {
  type: ErrorType;
  errorClass: ErrorClass;
  heading: string;
  message: string;
  handled: boolean;
}

export enum ErrorType {
  INTERNAL_SERVER_ERROR = 'INTERNAL_SERVER_ERROR',
  API_FORBIDDEN_RESPONSE = 'API_FORBIDDEN_RESPONSE',
  API_BAD_REQUEST = 'API_BAD_REQUEST',
  PAGE_NOT_FOUND = 'PAGE_NOT_FOUND',
  API_UNEXPECTED_RESPONSE = 'API_UNEXPECTED_RESPONSE',
  NOT_AUTHORIZED_ERROR = 'NOT_AUTHORIZED_ERROR',
  API_CONNECTION_ERROR = 'API_CONNECTION_ERROR',
  CLIENT_ANGULAR_ERROR = 'CLIENT_ANGULAR_ERROR',
  CUSTOM_ERROR = 'CUSTOM_ERROR',
}

export enum ErrorClass {
  PageNotFound = 'page-not-found',
  AccessDenied = 'access-denied',
  ServerError = 'server-error',
  ServerDown = 'server-down',
}
