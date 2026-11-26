import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/api_response.dart';
import '../utils/app_constants.dart';

class ApiService {
  String? accessToken;
  String? refreshToken;

  Future<Map<String, dynamic>> get(String path) => _send('GET', path);

  Future<Map<String, dynamic>> post(String path, {Map<String, dynamic>? body}) =>
      _send('POST', path, body: body);

  Future<Map<String, dynamic>> put(String path, {Map<String, dynamic>? body}) =>
      _send('PUT', path, body: body);

  Future<Map<String, dynamic>> _send(
    String method,
    String path, {
    Map<String, dynamic>? body,
    bool retry = true,
  }) async {
    final uri = Uri.parse('${AppConstants.apiBaseUrl}$path');
    final headers = <String, String>{
      'Content-Type': 'application/json',
      if (accessToken != null) 'Authorization': 'Bearer $accessToken',
    };

    late http.Response response;
    if (method == 'GET') {
      response = await http.get(uri, headers: headers);
    } else if (method == 'POST') {
      response = await http.post(uri, headers: headers, body: body != null ? jsonEncode(body) : null);
    } else if (method == 'PUT') {
      response = await http.put(uri, headers: headers, body: body != null ? jsonEncode(body) : null);
    } else {
      throw UnsupportedError('Unsupported method: $method');
    }

    if (response.statusCode == 401 && retry && refreshToken != null && !path.startsWith('/auth/')) {
      final refreshed = await _tryRefresh();
      if (refreshed) {
        return _send(method, path, body: body, retry: false);
      }
    }

    final decoded = response.body.isNotEmpty
        ? jsonDecode(response.body) as Map<String, dynamic>
        : <String, dynamic>{};

    if (response.statusCode < 200 || response.statusCode >= 300) {
      final errors = decoded['errors'];
      final message = (errors is List && errors.isNotEmpty)
          ? errors.join(', ')
          : (decoded['message']?.toString() ?? 'Request failed (${response.statusCode})');
      throw ApiException(response.statusCode, message);
    }

    return decoded;
  }

  Future<bool> _tryRefresh() async {
    try {
      final uri = Uri.parse('${AppConstants.apiBaseUrl}/auth/refresh-token');
      final response = await http.post(
        uri,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'refreshToken': refreshToken}),
      );
      if (response.statusCode != 200) return false;
      final decoded = jsonDecode(response.body) as Map<String, dynamic>;
      final token = decoded['data']?['token']?.toString();
      if (token != null && token.isNotEmpty) {
        accessToken = token;
        return true;
      }
      return false;
    } catch (_) {
      return false;
    }
  }
}
