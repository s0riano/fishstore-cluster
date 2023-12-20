import ApiClient from "./api-client";
import { API_BASE_URL} from "../../../../../config";
import {ApiErrorParserService} from "../error-parser/api-error-parser.service";

const errorParser = new ApiErrorParserService();
const apiClient = new ApiClient(errorParser);

class ApiService {
    async fetchData(endpoint: string): Promise<any> {  // Assuming the API returns JSON data
        const url = `${API_BASE_URL}/${endpoint}`;
        return await apiClient.get(url);
    }

    async postData(endpoint: string, data: any): Promise<any> {  // Assuming the API returns JSON data
        const url = `${API_BASE_URL}/${endpoint}`;
        return await apiClient.post(url, data);
    }

    // ... Add more methods as needed for PUT, DELETE, etc.
}

export default new ApiService();
