import axios, { AxiosInstance } from 'axios';
import { CancelTokenSource } from 'axios';

/*const errorParser = new ApiErrorParserService();
const apiClient = new ApiClient(errorParser);*/

type CancellationCallback = () => void;

export class CancellationToken {
    public source: CancelTokenSource;  // Explicitly define the source field
    public cancellationCallbacks: CancellationCallback[] = [];

    constructor() {
        this.source = axios.CancelToken.source();
    }

    public cancel() {
        this.source.cancel('Request canceled.');
        this.cancellationCallbacks.forEach(cancelCallback => {
            cancelCallback();
        });
        this.cancellationCallbacks = [];
    }
}

export class ApiCancellationError {
    discriminator = 'IsCancellation';
}

class ApiClient {
    private errorParser: any /*ApiErrorParserService*/;
    private axiosInstance: any /*AxiosInstance*/;   // Explicitly define the axiosInstance field


    constructor(errorParser: any) {
        this.errorParser = errorParser;

        // Create an Axios instance if you need to set some defaults
        this.axiosInstance = axios.create();
    }

    async get(url: string, options?: any): Promise<any>  {
        try {
            const response = await this.axiosInstance.get(url, options);
            return response.data;
        } catch (error) {
            throw this.parseError(error);
        }
    }

    async post(
        url: string,
        body: any,
        options?: any,
        ct?: CancellationToken
    ): Promise<any> {
        try {
            const response = await this.axiosInstance.post(url, body, {
                ...options,
                cancelToken: ct?.source.token,
            });
            return response.data;
        } catch (error) {
            if (axios.isCancel(error)) {
                throw new ApiCancellationError();
            }
            throw this.parseError(error);
        }
    }

    async delete(url, options) {
        try {
            const response = await this.axiosInstance.delete(url, options);
            return response.data;
        } catch (error) {
            throw this.parseError(error);
        }
    }

    async put(url, body, options) {
        try {
            const response = await this.axiosInstance.put(url, body, options);
            return response.data;
        } catch (error) {
            throw this.parseError(error);
        }
    }

    async getHeaders(url, options) {
        try {
            const response = await this.axiosInstance.post(url, {}, {
                ...options,
                observe: 'response',
            });
            return response.headers;
        } catch (error) {
            throw this.parseError(error);
        }
    }

    parseError(error) {
        // Use your errorParser logic here, adapted for Axios errors
        return this.errorParser.parseHttpError(error);
    }
}

export default ApiClient;
